package com.sec.gen.next.serviceorchestrator.internal.notification.mapper;

import com.next.gen.api.Notification;
import com.next.gen.sec.model.NotificationModel;
import com.sec.gen.next.serviceorchestrator.internal.places.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface NotificationMapper {
    NotificationModel map(Notification notification);
    List<NotificationModel> mapList(List<Notification> list);

    Notification mapTo(NotificationModel notificationModel);
    List<Notification> mapTo(List<NotificationModel> list);

}
