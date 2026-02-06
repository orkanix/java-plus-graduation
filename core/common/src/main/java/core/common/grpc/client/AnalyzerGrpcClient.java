package core.common.grpc.client;

import grpc.telemetry.analyzer.RecommendationsControllerGrpc;
import grpc.telemetry.user_request.InteractionsCountRequestProto;
import grpc.telemetry.user_request.RecommendedEventProto;
import grpc.telemetry.user_request.SimilarEventsRequestProto;
import grpc.telemetry.user_request.UserPredictionsRequestProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class AnalyzerGrpcClient {

    @GrpcClient("analyzer")
    private RecommendationsControllerGrpc.RecommendationsControllerBlockingStub stub;

    public Stream<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto request) {

        try {
            Iterator<RecommendedEventProto> iterator = stub.getRecommendationsForUser(request);
            return asStream(iterator);
        } catch (Exception e) {
            log.error("Ошибка при получении рекомендованных Event: {}", e.getMessage());
        }

        return Stream.empty();
    }

    public Stream<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto request) {

        try {
            Iterator<RecommendedEventProto> iterator = stub.getSimilarEvents(request);
            return asStream(iterator);
        } catch (Exception e) {
            log.error("Ошибка при получении похожих Event: {}", e.getMessage());
        }

        return Stream.empty();

    }

    public Stream<RecommendedEventProto> getInteractionsCount(InteractionsCountRequestProto request) {

        try {
            Iterator<RecommendedEventProto> iterator = stub.getInteractionsCount(request);
            return asStream(iterator);
        } catch (Exception e) {
            log.error("Ошибка при вычислении Interaction: {}", e.getMessage());
        }

        return Stream.empty();
    }

    private Stream<RecommendedEventProto> asStream(Iterator<RecommendedEventProto> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false);
    }
}
