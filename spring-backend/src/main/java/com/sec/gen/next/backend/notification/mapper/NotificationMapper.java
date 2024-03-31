package com.sec.gen.next.backend.notification.mapper;

import com.sec.gen.next.backend.api.external.NotificationModel;
import com.next.gen.api.Notification;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface NotificationMapper {
    NotificationModel map(Notification notification);
    List<NotificationModel> map(List<Notification> list);

    Notification mapTo(NotificationModel notificationModel);
    List<Notification> mapTo(List<NotificationModel> list);

}
