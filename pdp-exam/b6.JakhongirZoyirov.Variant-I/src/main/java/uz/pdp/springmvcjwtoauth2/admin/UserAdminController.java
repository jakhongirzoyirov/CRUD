package uz.pdp.springmvcjwtoauth2.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springmvcjwtoauth2.entity.role.RoleEnum;
import uz.pdp.springmvcjwtoauth2.entity.role.RolePermissionEnum;

import java.util.Set;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {

    @GetMapping
    public String getPermissionList() {
        return "main";
    }

    @ResponseBody
    @GetMapping("/permission/list/{roleName}")
    public Set<RolePermissionEnum> getPermission(@PathVariable  String roleName) {
        return RoleEnum.valueOf(roleName).getPermissionEnums();
    }

}
