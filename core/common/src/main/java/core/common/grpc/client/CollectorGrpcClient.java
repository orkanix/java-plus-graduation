package core.common.grpc.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import grpc.telemetry.collector.UserActionControllerGrpc;
import grpc.telemetry.user_action.ActionTypeProto;
import grpc.telemetry.user_action.UserActionProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CollectorGrpcClient {

    @GrpcClient("collector")
    private UserActionControllerGrpc.UserActionControllerFutureStub stub;

    public void sendEvent(long userId, long eventId, ActionTypeProto actionType) {
        UserActionProto request = UserActionProto.newBuilder()
                .setUserId((int) userId)
                .setEventId((int) eventId)
                .setActionType(actionType)
                .build();

        ListenableFuture<Empty> future = stub.collectUserAction(request);

        try {
            future.get();
            System.out.println("Сообщение успешно отправлено!");
        } catch (Exception e) {
            System.out.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }
}
