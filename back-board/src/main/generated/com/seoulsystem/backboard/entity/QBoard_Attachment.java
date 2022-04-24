package com.seoulsystem.backboard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard_Attachment is a Querydsl query type for Board_Attachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard_Attachment extends EntityPathBase<Board_Attachment> {

    private static final long serialVersionUID = 2143156864L;

    public static final QBoard_Attachment board_Attachment = new QBoard_Attachment("board_Attachment");

    public final NumberPath<Integer> ano = createNumber("ano", Integer.class);

    public final StringPath deleteFlag = createString("deleteFlag");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath orgName = createString("orgName");

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final StringPath saveName = createString("saveName");

    public final StringPath savePath = createString("savePath");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public QBoard_Attachment(String variable) {
        super(Board_Attachment.class, forVariable(variable));
    }

    public QBoard_Attachment(Path<? extends Board_Attachment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard_Attachment(PathMetadata metadata) {
        super(Board_Attachment.class, metadata);
    }

}

