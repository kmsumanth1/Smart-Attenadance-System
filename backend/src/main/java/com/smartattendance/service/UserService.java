package com.smartattendance.service;

import com.smartattendance.dto.*;
import com.smartattendance.exception.ResourceNotFoundException;
import com.smartattendance.model.AppUser;
import com.smartattendance.repository.AppUserRepository;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository repo; private final PasswordEncoder encoder;
    public UserService(AppUserRepository repo, PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}
    public Page<UserResponse> search(String q, Pageable p){return (q==null||q.isBlank()?repo.findAll(p):repo.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q,q,p)).map(this::toResponse);}
    public UserResponse get(Long id){return toResponse(find(id));}
    public UserResponse create(UserRequest r){AppUser u=AppUser.builder().email(r.email()).password(encoder.encode(r.password())).fullName(r.fullName()).role(r.role()).active(r.active()==null||r.active()).build();return toResponse(repo.save(u));}
    public UserResponse update(Long id, UserRequest r){AppUser u=find(id);u.setEmail(r.email());u.setFullName(r.fullName());u.setRole(r.role());u.setActive(r.active()==null||r.active());if(r.password()!=null&&!r.password().isBlank())u.setPassword(encoder.encode(r.password()));return toResponse(repo.save(u));}
    public void delete(Long id){repo.delete(find(id));}
    public AppUser find(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User",id));}
    public UserResponse toResponse(AppUser u){return new UserResponse(u.getId(),u.getEmail(),u.getFullName(),u.getRole(),u.isActive());}
}
