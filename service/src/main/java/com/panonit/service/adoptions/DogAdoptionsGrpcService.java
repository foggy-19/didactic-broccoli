package com.panonit.service.adoptions;

import com.google.protobuf.Empty;
import com.panonit.service.adoptions.grpc.AdoptionsGrpc;
import com.panonit.service.adoptions.grpc.Dog;
import com.panonit.service.adoptions.grpc.DogsResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
class DogAdoptionsGrpcService extends AdoptionsGrpc.AdoptionsImplBase {

    private final DogAdoptionService service;

    DogAdoptionsGrpcService(DogAdoptionService service) {
        this.service = service;
    }


    @Override
    public void all(Empty request, StreamObserver<DogsResponse> responseObserver) {
        var all = service.dogs().stream().map(dog -> Dog.newBuilder()
                .setId(dog.id())
                .setName(dog.name())
                .setDescription(dog.description())
                .build()
        ).toList();

        var reply = DogsResponse.newBuilder().addAllDogs(all).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
