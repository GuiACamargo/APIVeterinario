package com.gft.veterinario.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.veterinario.dto.veterinario.RegistroVeterinarioComSenhaDTO;
import com.gft.veterinario.dto.veterinario.RegistroVeterinarioDTO;
import com.gft.veterinario.dto.veterinario.VeterinarioMapper;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.exceptions.AlreadyExistsException;
import com.gft.veterinario.exceptions.DeleteException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.exceptions.InvalidCpfException;
import com.gft.veterinario.exceptions.LackOfSpaceException;
import com.gft.veterinario.exceptions.MissingInformationsException;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.PerfilRepository;
import com.gft.veterinario.repositories.UsuarioRepository;
import com.gft.veterinario.repositories.VeterinarioRepository;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class VeterinarioService {

	private final VeterinarioRepository veterinarioRepository;
	
	private final AtendimentoRepository atendimentoRepository;
	
	private final PerfilRepository perfilRepository;
	
	private final UsuarioRepository usuarioRepository;

	public VeterinarioService(VeterinarioRepository veterinarioRepository, AtendimentoRepository atendimentoRepository,
			PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
		this.veterinarioRepository = veterinarioRepository;
		this.atendimentoRepository = atendimentoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public Veterinario salvarVeterinario (Veterinario veterinario) {
		return veterinarioRepository.save(veterinario);
	}
	
	public Page<Veterinario> listarTodosOsVeterinarios (Pageable pageable) {
		return veterinarioRepository.findAll(pageable);
	}
	
	public Veterinario buscarVeterinario(Long id) {
		Optional<Veterinario> veterinario = veterinarioRepository.findById(id);
		
		return veterinario.orElseThrow(() -> new EntityMissingException("Veterinário não encontrado!"));
	}
	
	public Veterinario buscarVeterinarioPeloCpf(String cpf) {
		Optional<Veterinario> veterinario = veterinarioRepository.findByCpf(cpf);
		
		return veterinario.orElseThrow(() -> new EntityMissingException("Veterinário não encontrado!"));
	}
	
	public void excluirVeterinario(Long id) {
		Veterinario veterinario = buscarVeterinario(id);

		if (veterinario != null) {
			if(veterinario.getAtendimentos() != null) {
				veterinario.getAtendimentos().forEach(a -> {
					a.setVeterinario(null);
					a.setNomeVeterinario(null);
				});
			}
			veterinarioRepository.deleteById(id);
		} else {
			throw new DeleteException("Erro ao deletar veterinário");
		}
		
	}
	
	public boolean validaCpf(String cpf){ 
	    CPFValidator cpfValidator = new CPFValidator();
	    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
	    if(erros.size() > 0) { 
	    	return false; 
	    } else { 
	    	return true; 
	    }
	}
	
	public Veterinario criaVeterinarioComAtendimento (RegistroVeterinarioComSenhaDTO dto) {
		Veterinario veterinario = VeterinarioMapper.fromDTOComSenha(dto);
		Optional<Perfil> perfil = perfilRepository.findByNome("veterinario");
		if(validaCpf(veterinario.getCpf())) {
			if(perfil.isPresent()) {
				if(dto.getCpfUsadoParaLogin() != null && dto.getSenhaUsadaParaLogin() != null) {
					Usuario usuario = new Usuario(null, dto.getCpfUsadoParaLogin(), new BCryptPasswordEncoder().encode(dto.getSenhaUsadaParaLogin()) , perfil.get());
					usuarioRepository.save(usuario);
				} else {
					throw new MissingInformationsException("Diga um CPF e uma senha");
				}
			} else {
				if(dto.getCpfUsadoParaLogin() != null && dto.getSenhaUsadaParaLogin() != null) {
					Perfil perfilCliente = new Perfil(null, "veterinario");
					Usuario usuario = new Usuario(null, dto.getCpfUsadoParaLogin(), new BCryptPasswordEncoder().encode(dto.getSenhaUsadaParaLogin()) , perfilCliente);
					usuarioRepository.save(usuario);
				} else {
					throw new MissingInformationsException("Diga um CPF e uma senha");
				}
			}
			if(dto.getAtendimentosId() != null) {
				dto.getAtendimentosId().forEach(a -> {
					if(atendimentoRepository.findById(a).isPresent()) {
						Atendimento atendimento = atendimentoRepository.findById(a).get();
						List<Atendimento> atendimentosParaVeterinario = veterinario.getAtendimentos();
						if(atendimento.getVeterinario() == null) {
							if(atendimentosParaVeterinario == null) {
								atendimentosParaVeterinario = new ArrayList<>(Arrays.asList(atendimento));
							} else {
								atendimentosParaVeterinario.add(atendimento);
							}
							atendimento.setVeterinario(veterinario);
							veterinario.setAtendimentos(atendimentosParaVeterinario);
	
						} else {
							throw new LackOfSpaceException("Esse atendimento já possui um veterinário cadastrado!");
						}
					} else {
						throw new EntityMissingException("Atendimento não encontrado!");
					}
				});
			}
			if(veterinarioRepository.findByCpf(veterinario.getCpf()).isEmpty()) {
				return salvarVeterinario(veterinario);
			} else {
				throw new AlreadyExistsException("Já existe um Veterinário com esse CPF");
			}
		} else {
			throw new InvalidCpfException("Cpf inválido!");
		}
	}
	
	public Veterinario alteraVeterinario (RegistroVeterinarioDTO dto, Long id) {
		Veterinario veterinario = VeterinarioMapper.fromDTO(dto);
		Veterinario veterinarioOriginal = buscarVeterinario(id);
		if(validaCpf(veterinario.getCpf())) {
			if(veterinarioOriginal.getAtendimentos() != null) {
				veterinarioOriginal.getAtendimentos().forEach(a -> {
					a.setVeterinario(null);
					a.setNomeVeterinario(null);
				});
			}
			if(dto.getAtendimentosId() != null) {
				dto.getAtendimentosId().forEach(a -> {
					if(atendimentoRepository.findById(a).isPresent()) {
						Atendimento atendimento = atendimentoRepository.findById(a).get();
						List<Atendimento> atendimentosParaVeterinario = veterinario.getAtendimentos();
						if(atendimento.getVeterinario() == null) {
							if(atendimentosParaVeterinario == null) {
								atendimentosParaVeterinario = new ArrayList<>(Arrays.asList(atendimento));
							} else {
								atendimentosParaVeterinario.add(atendimento);
							}
							atendimento.setVeterinario(veterinario);
							veterinario.setAtendimentos(atendimentosParaVeterinario);
	
						} else {
							throw new LackOfSpaceException("Esse atendimento já possui um veterinário cadastrado!");
						}
					} else {
						throw new EntityMissingException("Atendimento não encontrado!");
					}
				});
			}
			veterinario.setId(veterinarioOriginal.getId());
			if(veterinarioRepository.findByCpf(veterinario.getCpf()).isEmpty()) {
				return salvarVeterinario(veterinario);
			} else {
				throw new AlreadyExistsException("Já existe um Veterinário com esse CPF");
			}
		} else {
			throw new InvalidCpfException("Cpf inválido!");
		}
	}	
}
