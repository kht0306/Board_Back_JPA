package com.seoulsystem.backboard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1725114146L;

    public static final QBoard board = new QBoard("board");

    public final StringPath attachment_flag = createString("attachment_flag");

    public final StringPath content = createString("content");

    public final StringPath deleteFlag = createString("deleteFlag");

    public final StringPath detailAddr = createString("detailAddr");

    public final StringPath email = createString("email");

    public final StringPath jibunAddr = createString("jibunAddr");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath password = createString("password");

    public final StringPath postcode = createString("postcode");

    public final StringPath refAddr = createString("refAddr");

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final StringPath roadAddr = createString("roadAddr");

    public final StringPath sort = createString("sort");

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

