package telemetry.service.analyzer.service;

import grpc.telemetry.user_request.InteractionsCountRequestProto;
import grpc.telemetry.user_request.RecommendedEventProto;
import grpc.telemetry.user_request.SimilarEventsRequestProto;
import grpc.telemetry.user_request.UserPredictionsRequestProto;

import java.util.stream.Stream;

public interface AnalyzerService {

    Stream<RecommendedEventProto> getRecommendationsForUser(UserPredictionsRequestProto request);

    Stream<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto request);

    Stream<RecommendedEventProto> getInteractionsCount(InteractionsCountRequestProto request);

}
