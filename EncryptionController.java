package com.encryption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.SecretKey;

@Controller
public class EncryptionController {

    private SecretKey secretKey;

    public EncryptionController() {
        try {
            secretKey = DESEncryption.generateKey();
        } catch (Exception e) {
            // Handle exception if key generation fails
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("inputText", "");
        model.addAttribute("outputText", "");
        model.addAttribute("charCount", 500);
        return "index";
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam("inputText") String inputText, Model model) {
        try {
            String encryptedText = DESEncryption.encrypt(inputText, secretKey);
            model.addAttribute("inputText", inputText);
            model.addAttribute("outputText", encryptedText);
        } catch (Exception e) {
            model.addAttribute("error", "Error during encryption: " + e.getMessage());
        }
        model.addAttribute("charCount", Math.max(500 - (inputText != null ? inputText.length() : 0), 0));
        return "index";
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam("outputText") String outputText, Model model) {
        try {
            String decryptedText = DESEncryption.decrypt(outputText, secretKey);
            model.addAttribute("inputText", decryptedText);
            model.addAttribute("outputText", outputText);
        } catch (Exception e) {
            model.addAttribute("error", "Error during decryption: " + e.getMessage());
        }
        model.addAttribute("charCount", Math.max(500 - (outputText != null ? outputText.length() : 0), 0));
        return "index";
    }
}
