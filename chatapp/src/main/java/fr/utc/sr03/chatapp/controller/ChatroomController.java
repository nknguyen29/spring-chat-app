package fr.utc.sr03.chatapp.controller;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.service.ChatroomService;
import fr.utc.sr03.chatapp.util.CustomCollectors;
import fr.utc.sr03.chatapp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/chatrooms")
public class ChatroomController {

    // private final ChatroomService chatroomService;
    // private final UserRepository userRepository;

    // public ChatroomController(final ChatroomService chatroomService,
    //         final UserRepository userRepository) {
    //     this.chatroomService = chatroomService;
    //     this.userRepository = userRepository;
    // }

    // @ModelAttribute
    // public void prepareContext(final Model model) {
    //     model.addAttribute("usersValues", userRepository.findAll(Sort.by("id"))
    //             .stream()
    //             .collect(CustomCollectors.toSortedMap(User::getId, User::getFirstName)));
    // }

    // // @GetMapping
    // // public String list(final Model model) {
    // //     model.addAttribute("chatrooms", chatroomService.findAll());
    // //     return "chatroom/list";
    // // }

    // @GetMapping("/add")
    // public String add(@ModelAttribute("chatroom") final ChatroomDTO chatroomDTO) {
    //     return "chatroom/add";
    // }

    // @PostMapping("/add")
    // public String add(@ModelAttribute("chatroom") @Valid final ChatroomDTO chatroomDTO,
    //         final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
    //     if (bindingResult.hasErrors()) {
    //         return "chatroom/add";
    //     }
    //     chatroomService.create(chatroomDTO);
    //     redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chatroom.create.success"));
    //     return "redirect:/chatrooms";
    // }

    // @GetMapping("/edit/{id}")
    // public String edit(@PathVariable(name = "id") final Long id, final Model model) {
    //     model.addAttribute("chatroom", chatroomService.get(id));
    //     return "chatroom/edit";
    // }

    // @PostMapping("/edit/{id}")
    // public String edit(@PathVariable(name = "id") final Long id,
    //         @ModelAttribute("chatroom") @Valid final ChatroomDTO chatroomDTO,
    //         final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
    //     if (bindingResult.hasErrors()) {
    //         return "chatroom/edit";
    //     }
    //     chatroomService.update(id, chatroomDTO);
    //     redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("chatroom.update.success"));
    //     return "redirect:/chatrooms";
    // }

    // @PostMapping("/delete/{id}")
    // public String delete(@PathVariable(name = "id") final Long id,
    //         final RedirectAttributes redirectAttributes) {
    //     chatroomService.delete(id);
    //     redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("chatroom.delete.success"));
    //     return "redirect:/chatrooms";
    // }

}
