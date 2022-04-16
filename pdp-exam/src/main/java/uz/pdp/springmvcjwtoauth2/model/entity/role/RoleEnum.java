package uz.pdp.springmvcjwtoauth2.model.entity.role;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Set;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(Sets.newHashSet(RolePermissionEnum.BOOK_ADD, RolePermissionEnum.BOOK_EDIT)),
    USER(Sets.newHashSet(RolePermissionEnum.BOOK_LIST)),
    SUPER_ADMIN(Sets.newHashSet(RolePermissionEnum.BOOK_LIST,  RolePermissionEnum.BOOK_DELETE,RolePermissionEnum.BOOK_ADD, RolePermissionEnum.BOOK_EDIT));

    private final Set<RolePermissionEnum> permissionEnums;

}
