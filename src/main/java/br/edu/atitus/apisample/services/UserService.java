package br.edu.atitus.apisample.services;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.List;
import java.util.regex.Matcher;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	// Essa classe possui uma dependência de um objeto UserRepository
	private final UserRepository repository;
	
	// No método construtor existe a injeção de dependência
    public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	// Expressão regular para validar e-mails
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
	
	public UserEntity save(UserEntity newUser) throws Exception {
		
		//TODO Validar regras de negócio 
		if (newUser == null)
			throw new Exception("Objeto nulo!");
		
		if (newUser.getName() == null || newUser.getName().isEmpty())
			throw new Exception ("Nome inválido");
		newUser.setName(newUser.getName().trim());
		
		if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception ("Email inválido");
		newUser.setEmail(newUser.getEmail().trim());
		
		//Validar email com regex
		if(!isValidEmail(newUser.getEmail()))
			throw new Exception("Email inválido");
			
		newUser.setEmail(newUser.getEmail().trim());
		
		if(repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário com este email");
		
		//TODO Invocar método camada repository
		
		this.repository.save(newUser);
		return newUser;
	}
	
	public List<UserEntity> findAll() throws Exception{
		return repository.findAll();
	}
	
    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}