module Scoring {
    requires Common;
    requires java.net.http;

    provides dk.sdu.cbse.common.services.IPostEntityProcessingService
            with dk.sdu.cbse.scoring.ScoreClient;
}