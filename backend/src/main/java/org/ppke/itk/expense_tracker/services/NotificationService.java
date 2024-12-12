package org.ppke.itk.expense_tracker.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NotificationService {

    public List<String> getNotificationsByUser(Integer id) {

        return Arrays.asList("Budget exceeded", "New expense added", "Your subscription is expiring soon");
    }
}
