package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.commons.constants.Role;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseMemberEntity{

    // 게시판 Id
    @Id
    @Column(length = 30)
    private String bId;

    // 게시판명
    @Column(length = 60, nullable = false)
    private String bName;

    // 사용 여부
    @Column(name="isUse")
    private boolean use;

    // 1페이지당 게시글 수
    private int rowsOfPage = 20;

    // 게시글 하단 목록 노출
    private boolean showViewList;

    // 게시판 분류
    @Lob
    private String category;

    // 목록 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role listAccessRole = Role.ALL;

    // 글보기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role viewAccessRole = Role.ALL;

    // 글쓰기 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role writeAccessRole = Role.ALL;

    // 답글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role replyAccessRole = Role.ALL;

    // 댓글 접근 권한
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role commentAccessRole = Role.ALL;

    // 에디터 사용 여부
    private boolean useEditor;

    // 파일 첨부 사용 여부
    private boolean useAttachFile;

    // 이미저 첨부 사용 여부
    private boolean useAttachImage;

    // 글 작성 후 이동
    @Column(length = 10, nullable = false)
    private String locationAfterWriting = "view";

    // 답글 사용 여부
    private boolean useReply;

    // 댓글 사용 여부
    private boolean useComment;

    // 비회원 작성, 수정 모드 여부
    @Transient
    private boolean isGuest;

    // 게시판 스킨
    @Column(length = 20, nullable = false)
    private String skin = "default";

    /**
     * 게시판 분류 목룍
     *
      * @return
     */

    public String[] getCategories() {
        if (category == null) {
            return null;
        }
        String[] categories = category.replaceAll("\\r", "").trim().split("\\n");
        return categories;
    }
}