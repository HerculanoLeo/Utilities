package br.com.herculano.utilities.templates;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("rawtypes")
public class ServiceTemplate<E, JPA, M extends MessageTemplate> {

	protected JpaRepository repository;
	
	protected M message;

	public ServiceTemplate(JpaRepository repository, M message) {
		this.repository = repository;
		this.message = message;
	}

	@SuppressWarnings("unchecked")
	public E save(E entity) {
		return (E) repository.save(entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(E entity) {
		repository.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public Page<E> consulta(Pageable page) {
		return repository.findAll(page);
	}

	@SuppressWarnings("unchecked")
	public Optional<E> findById(Integer id) {
		return repository.findById(id);
	}

	@SuppressWarnings("unchecked")
	public E consultaPorId(Integer id) {

		Optional optional = repository.findById(id);

		if (!optional.isPresent()) {
			Object[] args = {id};
			
			throw new EntityNotFoundException(CommonMessageTemplate.getCodigo(message.getNotFound(), args));
		}

		return (E) optional.get();
	}

	@SuppressWarnings("unchecked")
	protected JPA getRepository() {
		return (JPA) repository;
	}
	
	protected M getMessage() {
		return (M) message;
	}

}
