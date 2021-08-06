package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRollBook is a Querydsl query type for RollBook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRollBook extends EntityPathBase<RollBook> {

    private static final long serialVersionUID = -248659256L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRollBook rollBook = new QRollBook("rollBook");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DatePath<java.sql.Date> date = createDate("date", java.sql.Date.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QRoomMember roomMember;

    public final StringPath state = createString("state");

    public QRollBook(String variable) {
        this(RollBook.class, forVariable(variable), INITS);
    }

    public QRollBook(Path<? extends RollBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRollBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRollBook(PathMetadata metadata, PathInits inits) {
        this(RollBook.class, metadata, inits);
    }

    public QRollBook(Class<? extends RollBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomMember = inits.isInitialized("roomMember") ? new QRoomMember(forProperty("roomMember"), inits.get("roomMember")) : null;
    }

}

