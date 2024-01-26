package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.exception.ErrorMessage;
import com.ssafy.saessak.oauth.exception.UnAuthorizedException;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;

    public User getUserByAuthentication() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(()-> new UnAuthorizedException(ErrorMessage.AUTHENTICATION_CODE_EXPIRED));
    }

    public void AuthenticationByObject(User user, Long id) {
        if(parentRepository.findById(user.getId()).isPresent()) {
            Parent parent = parentRepository.findById(user.getId()).get();
            Kid kid = kidRepository.findById(id).get();

            if(!kid.getParent().getId().equals(parent.getId())) {
                return;
            }
        }
        if(teacherRepository.findById(user.getId()).isPresent()) {
            Teacher teacher = teacherRepository.findById(user.getId()).get();
            Classroom classroom = classroomRepository.findById(user.getId()).get();

            if(teacher.getClassroom().getClassroomId().equals(classroom.getClassroomId()))
                return;
        }
        throw new UnAuthorizedException(ErrorMessage.AUTHENTICATION_CODE_EXPIRED);
    }

    public void AuthenticationById(User user, Long id) {
        if(parentRepository.findById(user.getId()).isPresent()) {
            Parent parent = parentRepository.findById(user.getId()).get();

            if(parent.getId() == id)
                return;
        }
        if(teacherRepository.findById(user.getId()).isPresent()) {
            Teacher teacher = teacherRepository.findById(user.getId()).get();

            if(teacher.getId().equals(id))
                return;
        }
        throw new UnAuthorizedException(ErrorMessage.AUTHENTICATION_CODE_EXPIRED);
    }
}
