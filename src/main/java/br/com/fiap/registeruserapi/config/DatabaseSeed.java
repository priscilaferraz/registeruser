package br.com.fiap.registeruserapi.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.fiap.registeruserapi.model.User;
import br.com.fiap.registeruserapi.repository.UserRepository;

@Configuration
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        userRepository.save(
            new User(
                "Caramelo Leal",
                "caramelo@fiap.com.br",
                passwordEncoder.encode("123")               
            )
        );

        userRepository.save(
            new User(
                "Eduardo Alves",
                "eduardo@fiap.com.br",
                passwordEncoder.encode("123")
            )
        );    

        userRepository.save(
            new User(
                "Mendes Teixeira",
                "administrador@fiap.com.br",
                passwordEncoder.encode("administrador")
            )
        );    

    }
    
}
