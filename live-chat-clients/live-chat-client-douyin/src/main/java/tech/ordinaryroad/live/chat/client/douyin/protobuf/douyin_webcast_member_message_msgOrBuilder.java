/*
 * MIT License
 *
 * Copyright (c) 2023 OrdinaryRoad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: douyin_webcast_member_message_msg.proto

package tech.ordinaryroad.live.chat.client.douyin.protobuf;

public interface douyin_webcast_member_message_msgOrBuilder extends
    // @@protoc_insertion_point(interface_extends:tech.ordinaryroad.live.chat.client.bilibili.protobuf.douyin_webcast_member_message_msg)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Common common = 1;</code>
   * @return Whether the common field is set.
   */
  boolean hasCommon();
  /**
   * <code>.Common common = 1;</code>
   * @return The common.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.CommonOuterClass.Common getCommon();
  /**
   * <code>.Common common = 1;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.CommonOuterClass.CommonOrBuilder getCommonOrBuilder();

  /**
   * <code>.User user = 2;</code>
   * @return Whether the user field is set.
   */
  boolean hasUser();
  /**
   * <code>.User user = 2;</code>
   * @return The user.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.UserOuterClass.User getUser();
  /**
   * <code>.User user = 2;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.UserOuterClass.UserOrBuilder getUserOrBuilder();

  /**
   * <code>uint64 memberCount = 3;</code>
   * @return The memberCount.
   */
  long getMemberCount();

  /**
   * <code>.User operator = 4;</code>
   * @return Whether the operator field is set.
   */
  boolean hasOperator();
  /**
   * <code>.User operator = 4;</code>
   * @return The operator.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.UserOuterClass.User getOperator();
  /**
   * <code>.User operator = 4;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.UserOuterClass.UserOrBuilder getOperatorOrBuilder();

  /**
   * <code>bool isSetToAdmin = 5;</code>
   * @return The isSetToAdmin.
   */
  boolean getIsSetToAdmin();

  /**
   * <code>bool isTopUser = 6;</code>
   * @return The isTopUser.
   */
  boolean getIsTopUser();

  /**
   * <code>int64 rankScore = 7;</code>
   * @return The rankScore.
   */
  long getRankScore();

  /**
   * <code>int64 topUserNo = 8;</code>
   * @return The topUserNo.
   */
  long getTopUserNo();

  /**
   * <code>int64 enterType = 9;</code>
   * @return The enterType.
   */
  long getEnterType();

  /**
   * <code>int64 action = 10;</code>
   * @return The action.
   */
  long getAction();

  /**
   * <code>string actionDescription = 11;</code>
   * @return The actionDescription.
   */
  java.lang.String getActionDescription();
  /**
   * <code>string actionDescription = 11;</code>
   * @return The bytes for actionDescription.
   */
  com.google.protobuf.ByteString
      getActionDescriptionBytes();

  /**
   * <code>int64 userId = 12;</code>
   * @return The userId.
   */
  long getUserId();

  /**
   * <pre>
   * EffectConfig effectConfig = 13;
   * </pre>
   *
   * <code>string popStr = 14;</code>
   * @return The popStr.
   */
  java.lang.String getPopStr();
  /**
   * <pre>
   * EffectConfig effectConfig = 13;
   * </pre>
   *
   * <code>string popStr = 14;</code>
   * @return The bytes for popStr.
   */
  com.google.protobuf.ByteString
      getPopStrBytes();

  /**
   * <pre>
   * EffectConfig enterEffectConfig = 15;
   * </pre>
   *
   * <code>.Image backgroundImage = 16;</code>
   * @return Whether the backgroundImage field is set.
   */
  boolean hasBackgroundImage();
  /**
   * <pre>
   * EffectConfig enterEffectConfig = 15;
   * </pre>
   *
   * <code>.Image backgroundImage = 16;</code>
   * @return The backgroundImage.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.ImageOuterClass.Image getBackgroundImage();
  /**
   * <pre>
   * EffectConfig enterEffectConfig = 15;
   * </pre>
   *
   * <code>.Image backgroundImage = 16;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.ImageOuterClass.ImageOrBuilder getBackgroundImageOrBuilder();

  /**
   * <code>.Image backgroundImageV2 = 17;</code>
   * @return Whether the backgroundImageV2 field is set.
   */
  boolean hasBackgroundImageV2();
  /**
   * <code>.Image backgroundImageV2 = 17;</code>
   * @return The backgroundImageV2.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.ImageOuterClass.Image getBackgroundImageV2();
  /**
   * <code>.Image backgroundImageV2 = 17;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.ImageOuterClass.ImageOrBuilder getBackgroundImageV2OrBuilder();

  /**
   * <code>.Text anchorDisplayText = 18;</code>
   * @return Whether the anchorDisplayText field is set.
   */
  boolean hasAnchorDisplayText();
  /**
   * <code>.Text anchorDisplayText = 18;</code>
   * @return The anchorDisplayText.
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.TextOuterClass.Text getAnchorDisplayText();
  /**
   * <code>.Text anchorDisplayText = 18;</code>
   */
  tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.TextOuterClass.TextOrBuilder getAnchorDisplayTextOrBuilder();

  /**
   * <pre>
   * PublicAreaCommon publicAreaCommon = 19;
   * </pre>
   *
   * <code>int64 userEnterTipType = 20;</code>
   * @return The userEnterTipType.
   */
  long getUserEnterTipType();

  /**
   * <code>int64 anchorEnterTipType = 21;</code>
   * @return The anchorEnterTipType.
   */
  long getAnchorEnterTipType();

  /**
   * <code>map&lt;string, string&gt; buriedPointMap = 22;</code>
   */
  int getBuriedPointMapCount();
  /**
   * <code>map&lt;string, string&gt; buriedPointMap = 22;</code>
   */
  boolean containsBuriedPointMap(
      java.lang.String key);
  /**
   * Use {@link #getBuriedPointMapMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getBuriedPointMap();
  /**
   * <code>map&lt;string, string&gt; buriedPointMap = 22;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getBuriedPointMapMap();
  /**
   * <code>map&lt;string, string&gt; buriedPointMap = 22;</code>
   */
  /* nullable */
java.lang.String getBuriedPointMapOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; buriedPointMap = 22;</code>
   */
  java.lang.String getBuriedPointMapOrThrow(
      java.lang.String key);
}
