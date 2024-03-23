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
// source: douyin_webcast_control_message_msg.proto

package tech.ordinaryroad.live.chat.client.douyin.protobuf;

public final class douyin_webcast_control_message_msgProto {
  private douyin_webcast_control_message_msgProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_tech_ordinaryroad_live_chat_client_douyin_protobuf_douyin_webcast_control_message_msg_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_tech_ordinaryroad_live_chat_client_douyin_protobuf_douyin_webcast_control_message_msg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n(douyin_webcast_control_message_msg.pro" +
      "to\0222tech.ordinaryroad.live.chat.client.d" +
      "ouyin.protobuf\032\014Common.proto\"M\n\"douyin_w" +
      "ebcast_control_message_msg\022\027\n\006common\030\001 \001" +
      "(\0132\007.Common\022\016\n\006status\030\002 \001(\005Be\n2tech.ordi" +
      "naryroad.live.chat.client.douyin.protobu" +
      "fB\'douyin_webcast_control_message_msgPro" +
      "toP\001\242\002\003GPBb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.CommonOuterClass.getDescriptor(),
        });
    internal_static_tech_ordinaryroad_live_chat_client_douyin_protobuf_douyin_webcast_control_message_msg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_tech_ordinaryroad_live_chat_client_douyin_protobuf_douyin_webcast_control_message_msg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_tech_ordinaryroad_live_chat_client_douyin_protobuf_douyin_webcast_control_message_msg_descriptor,
        new java.lang.String[] { "Common", "Status", });
    tech.ordinaryroad.live.chat.client.douyin.protobuf.dto.CommonOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}