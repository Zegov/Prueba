package com.sermaluc.prueba.apiuser.prueba.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sermaluc.prueba.apiuser.prueba.dto.PhoneDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.PhoneRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserDTO;
import com.sermaluc.prueba.apiuser.prueba.dto.UserRequestDTO;
import com.sermaluc.prueba.apiuser.prueba.model.Telefono;
import com.sermaluc.prueba.apiuser.prueba.model.User;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setCreated(user.getCreado());
        dto.setModified(user.getModificado());
        dto.setLastLogin(user.getUltimoLogin());
        dto.setToken(user.getToken());
        dto.setActive(user.isEstaActivo());
        
        if (user.getTelefonos() != null) {
            dto.setPhones(user.getTelefonos().stream()
                .map(this::toPhoneDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setNombre(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        
        if (dto.getPhones() != null) {
            user.setTelefonos(dto.getPhones().stream()
                .map(this::toPhoneEntity)
                .collect(Collectors.toList()));
        }
        
        return user;
    }

    private PhoneDTO toPhoneDTO(Telefono phone) {
        if (phone == null) {
            return null;
        }

        PhoneDTO dto = new PhoneDTO();
        dto.setNumber(phone.getNumeroFono());
        dto.setCityCode(phone.getCodCiudad());
        dto.setCountryCode(phone.getCodPais());
        return dto;
    }

    private Telefono toPhoneEntity(PhoneRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Telefono phone = new Telefono();
        phone.setNumeroFono(dto.getNumber());
        phone.setCodCiudad(dto.getCityCode());
        phone.setCodPais(dto.getCountryCode());
        return phone;
    }



}
