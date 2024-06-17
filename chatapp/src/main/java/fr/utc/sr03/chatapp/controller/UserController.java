package fr.utc.sr03.chatapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.utc.sr03.chatapp.model.UserPostDTO;
import fr.utc.sr03.chatapp.model.UserSearch;
import fr.utc.sr03.chatapp.service.UserService;
import fr.utc.sr03.chatapp.util.WebUtils;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(@Valid final UserSearch userSearch, final Model model) {
        model.addAttribute("users", userService.findAll(userSearch));
        model.addAttribute("userSearch", userSearch);
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserPostDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserPostDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        final Long id = userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users/" + id;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("user", userService.getProfile(id));
        return "user/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("user", userService.edit(id));
        return "user/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable(name = "id") final Long id,
            @ModelAttribute("user") @Valid final UserPostDTO userDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/users/{id}";
    }

    @GetMapping("/{id}/settings")
    public String settings(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("user", userService.get(id));
        return "user/settings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        return "redirect:/users";
    }

    @PostMapping("/delete-all")
    public String deleteAll(final RedirectAttributes redirectAttributes) {
        userService.deleteAll();
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete-all.success"));
        return "redirect:/users";
    }

    @PostMapping("/{id}/lock")
    public String lock(@PathVariable(name = "id") final Long id, final RedirectAttributes redirectAttributes) {
        userService.lock(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.lock.success"));
        return "redirect:/users/{id}";
    }

    @PostMapping("/{id}/unlock")
    public String unlock(@PathVariable(name = "id") final Long id, final RedirectAttributes redirectAttributes) {
        userService.unlock(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.unlock.success"));
        return "redirect:/users/{id}";
    }

}
