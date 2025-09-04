package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.models.Segment;
import com.gtelant.commerce_service.models.User;
import com.gtelant.commerce_service.models.UserSegment;
import com.gtelant.commerce_service.repositories.SegmentRepository;
import com.gtelant.commerce_service.repositories.UserRepository;
import com.gtelant.commerce_service.repositories.UserSegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSegmentRepository userSegmentRepository;
    private final SegmentRepository segmentRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserSegmentRepository userSegmentRepository, SegmentRepository segmentRepository) {
        this.userRepository = userRepository;
        this.userSegmentRepository = userSegmentRepository;
        this.segmentRepository = segmentRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Page<User> getAllUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public void assignSegmentToUser(int id, int segmentId) {
        Optional<Segment> segment = segmentRepository.findById(segmentId);
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent() && segment.isPresent() ) {
            UserSegment userSegment = new UserSegment();
            userSegment.setSegment(segment.get());
            userSegment.setUser(user.get());
            userSegmentRepository.save(userSegment);
        }
    }
}
