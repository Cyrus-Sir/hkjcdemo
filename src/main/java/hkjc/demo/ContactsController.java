package hkjc.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ContactsController {

    @Autowired
    private ContactsRepo repo;

    private static final Logger logger = LogManager.getLogger(ContactsController.class);

    @GetMapping({"","/","/index"})
    public String index(ModelMap m){
        m.addAttribute("allContacts", repo.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(ModelMap m){
        m.addAttribute("newContacts", new Contacts());
        return "create";
    }

    @PostMapping("/create")
    public String create(ModelMap m, @ModelAttribute("newContacts") Contacts _c){
        repo.save(_c);
        logger.info("New contact created : {}", _c.toString());
        return "redirect:/index";
    }

    @GetMapping("/edit/{cid}")
    public String edit(ModelMap m, @PathVariable("cid") Integer cid){
        Optional<Contacts> _oc = repo.findById(cid);
        if(!_oc.isPresent()){
            logger.info("No contact with Id : {}", cid);
            return "redirect:/index";
        }
        m.addAttribute("editContacts", _oc.get());
        logger.info("Editing contact with Id : {}", cid);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(ModelMap m, @ModelAttribute("editContacts") Contacts _c){
        repo.save(_c);
        logger.info("Updated contact : {}", _c.toString());
        return "redirect:/index";
    }

    @GetMapping("/delete/{cid}")
    public String delete(@PathVariable("cid") Integer cid){
        repo.deleteById(cid);
        logger.info("Contact with Id deleted : {}", cid);
        return "redirect:/index";
    }
}
