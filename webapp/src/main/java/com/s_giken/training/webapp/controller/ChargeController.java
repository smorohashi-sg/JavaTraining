package com.s_giken.training.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.s_giken.training.webapp.model.entity.ChargeSearchCondition;
import com.s_giken.training.webapp.service.IChargeService;

@Controller
@RequestMapping("/charge")
public class ChargeController {

    private final IChargeService chargeservice;
    public ChargeController(IChargeService chargeservice) {
        this.chargeservice = chargeservice;
    }

    @GetMapping("/search")
    public String showChargeCondition(@ModelAttribute ChargeSearchCondition chargeSearchCondition) {
        return "charge_search_condition";
    }

    @PostMapping("/search")
    public String searchAndListing(@ModelAttribute("chargeSearchCondition") ChargeSearchCondition chargeSearchCodition,
            Model model) {
        var result = chargeservice.findByConditions(chargeSearchCodition);
        model.addAttribute("result", result);
        return "member_search_result";
    }

}
