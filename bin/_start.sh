#!/bin/bash

java -jar -Dcom.evolv.cluster.seedNodes=${SEED_NODE} \
          -Dcom.evolv.cluster.nodeType=${NODE_TYPE} \
          -Dcom.evolv.cluster.hostname=${HOST_NAME} \
          /bin/AkkaClusterExample-assembly-1.0.jar