package com.wbm.scenergyspring.domain.user.entity;

import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
public class UserLocationTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "location_tag_id")
    private LocationTag locationTag;

   public void updateUser(User user) {
       this.user = user;
   }

   public void updateLocationTag(LocationTag locationTag) {
       this.locationTag = locationTag;
   }

}
