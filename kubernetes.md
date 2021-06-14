# container orchestration with k8n

available:

1. kubernetes (popular; difficult)
1. docker swarm (no out-of-the-box auto-scaling)
1. mesos (apache; difficult and advanced)

* terminology
  * nodes ... single host (physical or virtualized)
  * cluster ... set of nodes
  * master ... controlling the nodes
* components:
  * API server (access to kubernetes; e.g. via CLI)
  * etcd (key-value-store)
  * kubelet (each node’s agent in the cluster, checking containers running on node)
  * container runtime (well... runs containers; e.g.: docker)
  * controller (the “brain”, decides rerunning crashed nodes)
  * scheduler (work distribution)

# commands

* basic kubectl commands: { `get, describe, logs, exec` }
* show general info: `kubectl cluster-info`
* list nodes: `kubectl get nodes`
* list pods: `kubectl get pods`
* info pod: `kubectl describe pods`

* run service: `kubectl run --replicase=10 IMAGE`
* scale up: `kubectl scale --replicas=20 IMAGE`
* upgrade: `kubectl rolling-update IMAGE --image=IMAGE2`
* roll back: `kubectl rolling-update IMAGE --rollback`
