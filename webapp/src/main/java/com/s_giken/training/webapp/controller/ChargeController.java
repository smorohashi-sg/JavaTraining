package com.s_giken.training.webapp.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.s_giken.training.webapp.exception.NotFoundException;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchCondition;
import com.s_giken.training.webapp.model.form.DeleteChargeForm;
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
        return "charge_search_result";
    }

    @GetMapping("/edit/{id}")
    public String editCharge(@PathVariable Long id, Model model) {
        var charge = chargeservice.findById(id);
        if (!charge.isPresent()) {
            throw new NotFoundException(String.format("指定したchargeId(%d)の料金情報が存在しません。", id));
        }
        model.addAttribute("isAddMode", false);
        model.addAttribute("charge", charge.get());
        return "charge_edit";
    }

    @GetMapping("/add")
    public String formAddCharge(Model model) {
        var charge = new Charge();
        model.addAttribute("isAddMode", true);
        model.addAttribute("charge", charge);
        return "charge_edit";
    }

    @PostMapping("/add")
    @Transactional
    public String addCharge(@Validated Charge charge,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (charge.getStartDate() != null && charge.getEndDate() != null
                && charge.getEndDate().isBefore(charge.getStartDate())) {
            bindingResult.rejectValue("endDate", "date.range", "終了日は開始日以降の日付を入力してください。");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isAddMode", true);
            return "charge_edit";
        }
        chargeservice.add(charge);
        redirectAttributes.addFlashAttribute("message", "保存しました。");
        return "redirect:/charge/edit/" + charge.getChargeid();

    }

    @PostMapping("/update")
    @Transactional
    public String saveCharge(@Validated Charge charge,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (charge.getStartDate() != null && charge.getEndDate() != null
                && charge.getEndDate().isBefore(charge.getStartDate())) {
            bindingResult.rejectValue("endDate", "date.range", "終了日は開始日以降の日付を入力してください。");
        }

        if (bindingResult.hasErrors()) {
            return "charge_edit";
        }
        chargeservice.update(charge);
        redirectAttributes.addFlashAttribute("message", "保存しました。");
        return "redirect:/charge/edit/" + charge.getChargeid();
    }

    @PostMapping("/delete")
    public String deleteCharge(@Validated DeleteChargeForm deleteUser,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            throw new NotFoundException(String.format("chargeIdが不正です。"));
        }

        Long chargeid = deleteUser.getChargeid();
        Optional<Charge> charge = chargeservice.findById(chargeid);
        if (!charge.isPresent()) {
            throw new NotFoundException(String.format("指定したchargeId(%d)の加入者情報が存在しません。", charge));
        }

        chargeservice.deleteById(chargeid);
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        return "redirect:/charge/search";
    }

}
