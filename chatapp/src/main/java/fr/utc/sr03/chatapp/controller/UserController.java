package fr.utc.sr03.chatapp.controller;

import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.model.UserAddDTO;
import fr.utc.sr03.chatapp.service.ChatroomService;
import fr.utc.sr03.chatapp.service.UserService;
import fr.utc.sr03.chatapp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ChatroomService chatroomService;

    public UserController(final UserService userService,
            final ChatroomService chatroomService) {
        this.userService = userService;
        this.chatroomService = chatroomService;
    }

    @GetMapping
    public String list(
        @RequestParam(name = "search", required = false) final String search,
        @RequestParam(name = "sort_by", required = false, defaultValue = "id") final String sortBy,
        @RequestParam(name = "sort_order", required = false, defaultValue = "asc") final String sortOrder,
        @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size,
        @RequestParam(name = "is_admin", required = false) final Boolean isAdmin,
        @RequestParam(name = "is_locked", required = false) final Boolean isLocked,
        final Model model
    ) {
        model.addAttribute("users", userService.search(search, sortBy, sortOrder, page, size, isAdmin, isLocked));
        model.addAttribute("search", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isLocked", isLocked);
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserAddDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserAddDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/users";
    }

    // @GetMapping("/edit/{id}")
    // public String edit(@PathVariable(name = "id") final Long id, final Model model) {
    //     model.addAttribute("user", userService.getWithoutChatroom(id));
    //     return "user/edit";
    // }

    // @PutMapping("/edit/{id}")
    // public String edit(@PathVariable(name = "id") final Long id,
    //         @ModelAttribute("user") @Valid final UserWithoutChatroomDTO userDTO, final BindingResult bindingResult,
    //         final RedirectAttributes redirectAttributes) {
    //     if (bindingResult.hasErrors()) {
    //         return "user/edit";
    //     }
    //     userService.update(id, userDTO);
    //     redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
    //     return "redirect:/users";
    // }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        return "redirect:/users";
    }

    // @GetMapping("/chatrooms/{id}")
    // public String chatrooms(@PathVariable(name = "id") final Long id, final Model model) {
    //     model.addAttribute("user", userService.getPublic(id));
    //     return "user/chatrooms";
    // }

    // @GetMapping("/chatrooms/{id}/edit")
    // public String editChatrooms(@PathVariable(name = "id") final Long id, final Model model) {
    //     model.addAttribute("user", userService.getPublic(id));
    //     model.addAttribute("chatrooms", chatroomService.findAllWithoutUser());
    //     return "user/edit-chatrooms";
    // }

    // @PutMapping("/chatrooms/{id}/edit")
    // public String editChatrooms(@PathVariable(name = "id") final Long id,
    //         @ModelAttribute("user") final UserDTO userDTO, @ModelAttribute("chatrooms") final List<ChatroomWithoutUserDTO> chatrooms,
    //         final RedirectAttributes redirectAttributes) {
    //     userService.updateChatrooms(id, userDTO, chatrooms);
    //     redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.chatrooms.update.success"));
    //     return "redirect:/users";
    // }

    @GetMapping("/__debug")
    public String debug(final Model model) {
        model.addAttribute("debug", userService.findAllWithStats());
        return "debug";
    }

}
