package uz.pdp.springmvcjwtoauth2.entity.role;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Set;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(Sets.newHashSet(COURSE_ADD, COURSE_EDIT, COURSE_DELETE)),
    USER(Sets.newHashSet(COURSE_LIST, COURSE_EDIT)),
    SUPER_ADMIN(Sets.newHashSet());

    private final Set<RolePermissionEnum> permissionEnums;

}
