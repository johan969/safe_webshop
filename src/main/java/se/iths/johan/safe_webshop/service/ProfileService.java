package se.iths.johan.safe_webshop.service;

import se.iths.johan.springmessenger.service.MessageService;

public class ProfileService {

    private MessageService messageService;

    public ProfileService(MessageService messageService) {
        this.messageService = messageService;
    }
}
