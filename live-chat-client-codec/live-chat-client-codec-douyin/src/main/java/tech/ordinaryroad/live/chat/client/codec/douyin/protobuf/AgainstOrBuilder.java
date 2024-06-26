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

public interface AgainstOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Against)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string leftName = 1;</code>
   * @return The leftName.
   */
  String getLeftName();
  /**
   * <code>string leftName = 1;</code>
   * @return The bytes for leftName.
   */
  com.google.protobuf.ByteString
      getLeftNameBytes();

  /**
   * <code>.Image leftLogo = 2;</code>
   * @return Whether the leftLogo field is set.
   */
  boolean hasLeftLogo();
  /**
   * <code>.Image leftLogo = 2;</code>
   * @return The leftLogo.
   */
  Image getLeftLogo();
  /**
   * <code>.Image leftLogo = 2;</code>
   */
  ImageOrBuilder getLeftLogoOrBuilder();

  /**
   * <code>string leftGoal = 3;</code>
   * @return The leftGoal.
   */
  String getLeftGoal();
  /**
   * <code>string leftGoal = 3;</code>
   * @return The bytes for leftGoal.
   */
  com.google.protobuf.ByteString
      getLeftGoalBytes();

  /**
   * <pre>
   *  LeftPlayersList leftPlayersList = 4;
   *  LeftGoalStageDetail leftGoalStageDetail = 5;
   * </pre>
   *
   * <code>string rightName = 6;</code>
   * @return The rightName.
   */
  String getRightName();
  /**
   * <pre>
   *  LeftPlayersList leftPlayersList = 4;
   *  LeftGoalStageDetail leftGoalStageDetail = 5;
   * </pre>
   *
   * <code>string rightName = 6;</code>
   * @return The bytes for rightName.
   */
  com.google.protobuf.ByteString
      getRightNameBytes();

  /**
   * <code>.Image rightLogo = 7;</code>
   * @return Whether the rightLogo field is set.
   */
  boolean hasRightLogo();
  /**
   * <code>.Image rightLogo = 7;</code>
   * @return The rightLogo.
   */
  Image getRightLogo();
  /**
   * <code>.Image rightLogo = 7;</code>
   */
  ImageOrBuilder getRightLogoOrBuilder();

  /**
   * <code>string rightGoal = 8;</code>
   * @return The rightGoal.
   */
  String getRightGoal();
  /**
   * <code>string rightGoal = 8;</code>
   * @return The bytes for rightGoal.
   */
  com.google.protobuf.ByteString
      getRightGoalBytes();

  /**
   * <pre>
   *  RightPlayersList rightPlayersList  = 9;
   *  RightGoalStageDetail rightGoalStageDetail = 10;
   * </pre>
   *
   * <code>uint64 timestamp = 11;</code>
   * @return The timestamp.
   */
  long getTimestamp();

  /**
   * <code>uint64 version = 12;</code>
   * @return The version.
   */
  long getVersion();

  /**
   * <code>uint64 leftTeamId = 13;</code>
   * @return The leftTeamId.
   */
  long getLeftTeamId();

  /**
   * <code>uint64 rightTeamId = 14;</code>
   * @return The rightTeamId.
   */
  long getRightTeamId();

  /**
   * <code>uint64 diffSei2absSecond = 15;</code>
   * @return The diffSei2absSecond.
   */
  long getDiffSei2AbsSecond();

  /**
   * <code>uint32 finalGoalStage = 16;</code>
   * @return The finalGoalStage.
   */
  int getFinalGoalStage();

  /**
   * <code>uint32 currentGoalStage = 17;</code>
   * @return The currentGoalStage.
   */
  int getCurrentGoalStage();

  /**
   * <code>uint32 leftScoreAddition = 18;</code>
   * @return The leftScoreAddition.
   */
  int getLeftScoreAddition();

  /**
   * <code>uint32 rightScoreAddition = 19;</code>
   * @return The rightScoreAddition.
   */
  int getRightScoreAddition();

  /**
   * <code>uint64 leftGoalInt = 20;</code>
   * @return The leftGoalInt.
   */
  long getLeftGoalInt();

  /**
   * <code>uint64 rightGoalInt = 21;</code>
   * @return The rightGoalInt.
   */
  long getRightGoalInt();
}