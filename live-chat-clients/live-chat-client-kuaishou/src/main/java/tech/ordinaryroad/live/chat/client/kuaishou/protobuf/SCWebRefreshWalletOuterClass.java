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
// source: SCWebRefreshWallet.proto

package tech.ordinaryroad.live.chat.client.kuaishou.protobuf;

public final class SCWebRefreshWalletOuterClass {
  private SCWebRefreshWalletOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SCWebRefreshWalletOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SCWebRefreshWallet)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code SCWebRefreshWallet}
   */
  public static final class SCWebRefreshWallet extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:SCWebRefreshWallet)
      SCWebRefreshWalletOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use SCWebRefreshWallet.newBuilder() to construct.
    private SCWebRefreshWallet(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private SCWebRefreshWallet() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new SCWebRefreshWallet();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.internal_static_SCWebRefreshWallet_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.internal_static_SCWebRefreshWallet_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.class, tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.Builder.class);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet)) {
        return super.equals(obj);
      }
      tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet other = (tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet) obj;

      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code SCWebRefreshWallet}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:SCWebRefreshWallet)
        tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWalletOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.internal_static_SCWebRefreshWallet_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.internal_static_SCWebRefreshWallet_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.class, tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.Builder.class);
      }

      // Construct using tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.internal_static_SCWebRefreshWallet_descriptor;
      }

      @java.lang.Override
      public tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet getDefaultInstanceForType() {
        return tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.getDefaultInstance();
      }

      @java.lang.Override
      public tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet build() {
        tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet buildPartial() {
        tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet result = new tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet(this);
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet) {
          return mergeFrom((tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet other) {
        if (other == tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:SCWebRefreshWallet)
    }

    // @@protoc_insertion_point(class_scope:SCWebRefreshWallet)
    private static final tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet();
    }

    public static tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<SCWebRefreshWallet>
        PARSER = new com.google.protobuf.AbstractParser<SCWebRefreshWallet>() {
      @java.lang.Override
      public SCWebRefreshWallet parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<SCWebRefreshWallet> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<SCWebRefreshWallet> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public tech.ordinaryroad.live.chat.client.kuaishou.protobuf.SCWebRefreshWalletOuterClass.SCWebRefreshWallet getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SCWebRefreshWallet_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SCWebRefreshWallet_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030SCWebRefreshWallet.proto\"\024\n\022SCWebRefre" +
      "shWalletB6\n4tech.ordinaryroad.live.chat." +
      "client.kuaishou.protobufb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_SCWebRefreshWallet_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_SCWebRefreshWallet_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SCWebRefreshWallet_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}