package telemetry.service.collector.controller;

import com.google.protobuf.Empty;
import grpc.telemetry.collector.UserActionControllerGrpc;
import grpc.telemetry.user_action.ActionTypeProto;
import grpc.telemetry.user_action.UserActionProto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import telemetry.service.collector.service.CollectorService;

import java.time.Instant;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserActionController extends UserActionControllerGrpc.UserActionControllerImplBase {

    private final CollectorService service;

    @Override
    public void collectUserAction(UserActionProto request, StreamObserver<Empty> responseObserver) {
        try {
            log.info("Пришло новое действие {} пользователя с id: {}!", request.getActionType(), request.getUserId());
            service.sendUserAction(toAvro(request));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    private static UserActionAvro toAvro(UserActionProto proto) {
        return UserActionAvro.newBuilder()
                .setUserId(proto.getUserId())
                .setEventId(proto.getEventId())
                .setActionType(toAvroActionType(proto.getActionType()))
                .setTimestamp(Instant.ofEpochSecond(
                        proto.getTimestamp().getSeconds(),
                        proto.getTimestamp().getNanos()
                ))
                .build();
    }

    private static ActionTypeAvro toAvroActionType(ActionTypeProto proto) {
        switch (proto) {
            case ACTION_VIEW: return ActionTypeAvro.VIEW;
            case ACTION_REGISTER: return ActionTypeAvro.REGISTER;
            case ACTION_LIKE: return ActionTypeAvro.LIKE;
            default: throw new IllegalArgumentException("Несуществующее целевое действие: " + proto);
        }
    }

}
