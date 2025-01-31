package net.yigitak.ad_user_manager.controllers;

import net.yigitak.ad_user_manager.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllVendors() {
        var vendors = vendorService.getAll();
        return ResponseEntity.ok(vendors);
    }

}
