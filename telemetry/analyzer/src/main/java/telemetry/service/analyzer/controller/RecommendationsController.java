package telemetry.service.analyzer.controller;

import grpc.telemetry.analyzer.RecommendationsControllerGrpc;
import grpc.telemetry.user_request.InteractionsCountRequestProto;
import grpc.telemetry.user_request.RecommendedEventProto;
import grpc.telemetry.user_request.SimilarEventsRequestProto;
import grpc.telemetry.user_request.UserPredictionsRequestProto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import telemetry.service.analyzer.service.AnalyzerService;

import java.util.stream.Stream;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class RecommendationsController extends RecommendationsControllerGrpc.RecommendationsControllerImplBase {

    private final AnalyzerService service;

    @Override
    public void getRecommendationsForUser(UserPredictionsRequestProto request, StreamObserver<RecommendedEventProto> responseObserver) {

        try (Stream<RecommendedEventProto> result = service.getRecommendationsForUser(request)) {
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    @Override
    public void getSimilarEvents(SimilarEventsRequestProto request, StreamObserver<RecommendedEventProto> responseObserver) {

        try (Stream<RecommendedEventProto> result = service.getSimilarEvents(request)) {
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    @Override
    public void getInteractionsCount(InteractionsCountRequestProto request, StreamObserver<RecommendedEventProto> responseObserver) {

        try (Stream<RecommendedEventProto> result = service.getInteractionsCount(request)) {
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }
}
