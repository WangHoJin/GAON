package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuser is a Querydsl query type for Guser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGuser extends EntityPathBase<Guser> {

    private static final long serialVersionUID = 460156464L;

    public static final QGuser guser = new QGuser("guser");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath nickname = createString("nickname");

    public final ListPath<RoomMember, QRoomMember> roomMembers = this.<RoomMember, QRoomMember>createList("roomMembers", RoomMember.class, QRoomMember.class, PathInits.DIRECT2);

    public final ListPath<Room, QRoom> rooms = this.<Room, QRoom>createList("rooms", Room.class, QRoom.class, PathInits.DIRECT2);

    public QGuser(String variable) {
        super(Guser.class, forVariable(variable));
    }

    public QGuser(Path<? extends Guser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuser(PathMetadata metadata) {
        super(Guser.class, metadata);
    }

}

