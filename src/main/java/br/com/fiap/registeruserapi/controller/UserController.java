package br.com.fiap.registeruserapi.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.registeruserapi.model.User;
import br.com.fiap.registeruserapi.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    @Cacheable("user")
    public Page<User> index(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listAll(paginacao);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(service.getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable 
    Long id, @RequestBody User newUser){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        var user = optional.get();
        var password = user.getPassword();
        BeanUtils.copyProperties(newUser, user);
        user.setId(id);
        user.setPassword(password);
        service.save(user);
        return ResponseEntity.ok(user);
    }  

    @DeleteMapping("{id}")
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}