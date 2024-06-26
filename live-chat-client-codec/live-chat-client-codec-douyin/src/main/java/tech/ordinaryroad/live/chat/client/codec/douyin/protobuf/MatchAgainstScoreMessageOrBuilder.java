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
// source: douyin.proto

// Protobuf Java Version: 3.25.3
package tech.ordinaryroad.live.chat.client.codec.douyin.protobuf;

public interface MatchAgainstScoreMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MatchAgainstScoreMessage)
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
  tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.Common getCommon();
  /**
   * <code>.Common common = 1;</code>
   */
  tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.CommonOrBuilder getCommonOrBuilder();

  /**
   * <code>.Against against = 2;</code>
   * @return Whether the against field is set.
   */
  boolean hasAgainst();
  /**
   * <code>.Against against = 2;</code>
   * @return The against.
   */
  tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.Against getAgainst();
  /**
   * <code>.Against against = 2;</code>
   */
  tech.ordinaryroad.live.chat.client.codec.douyin.protobuf.AgainstOrBuilder getAgainstOrBuilder();

  /**
   * <code>uint32 matchStatus = 3;</code>
   * @return The matchStatus.
   */
  int getMatchStatus();

  /**
   * <code>uint32 displayStatus = 4;</code>
   * @return The displayStatus.
   */
  int getDisplayStatus();
}
