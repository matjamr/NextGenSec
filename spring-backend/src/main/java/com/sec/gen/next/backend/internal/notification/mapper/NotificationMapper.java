package com.sec.gen.next.backend.internal.notification.mapper;

import com.sec.gen.next.backend.internal.api.external.NotificationModel;
import com.sec.gen.next.backend.internal.api.internal.Notification;
import com.sec.gen.next.backend.internal.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface NotificationMapper {
    NotificationModel map(Notification notification);
    List<NotificationModel> map(List<Notification> list);

    Notification mapTo(NotificationModel notificationModel);
    List<Notification> mapTo(List<NotificationModel> list);

}
