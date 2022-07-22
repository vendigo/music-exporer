docker build -t dmmarchenko/music-explorer .

./gradlew bootBuildImage --imageName=dmmarchenko/music-explorer

docker run -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 --name MusicExplorer -t dmmarchenko/music-explorer 

Create Kubernetes cluster

gcloud container --project "learngcp-353414" clusters create-auto "music-explorer-cluster" --region "europe-central2" --release-channel "regular" --network "projects/learngcp-353414/global/networks/default" --subnetwork "projects/learngcp-353414/regions/europe-central2/subnetworks/default" --cluster-ipv4-cidr "/17" --services-ipv4-cidr "/22"


kubectl create secret generic artist-db-secret \
--from-literal=username=postgres \
--from-literal=password='"8&*$c}?2%U?j>Fy' \
--from-literal=database=postgres

Connect to SQL Auth proxy

https://cloud.google.com/sql/docs/postgres/connect-kubernetes-engine

gcloud iam service-accounts add-iam-policy-binding \
--role="roles/iam.workloadIdentityUser" \
--member="serviceAccount:learngcp-353414.svc.id.goog[default/music-explorer-ksa]" \
music-explorer@learngcp-353414.iam.gserviceaccount.com

kubectl annotate serviceaccount \
music-explorer-ksa \
iam.gke.io/gcp-service-account=music-explorer@learngcp-353414.iam.gserviceaccount.com