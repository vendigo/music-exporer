## TODOs

1. Deploy newly created image +
2. Connect to SQL via private IP +
3. Move passwords to secrets +
4. Configure SQL migration +
5. Complete init script
6. Draw diagrams

## Notes

### Create Docker Image

docker build -t dmmarchenko/music-explorer .

./gradlew bootBuildImage --imageName=dmmarchenko/music-explorer

docker run -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 --name MusicExplorer -t dmmarchenko/music-explorer 

### Create Cluster

Create Kubernetes cluster

gcloud container --project "learngcp-353414" clusters create-auto "music-explorer-cluster" --region "europe-central2" --release-channel "regular" --network "projects/learngcp-353414/global/networks/default" --subnetwork "projects/learngcp-353414/regions/europe-central2/subnetworks/default" --cluster-ipv4-cidr "/17" --services-ipv4-cidr "/22"

### Create Secret

kubectl create secret generic artist-db-secret \
--from-literal=username=postgres \
--from-literal=password='"8&*$c}?2%U?j>Fy' \
--from-literal=database=postgres

### Connect to SQL Auth proxy

https://cloud.google.com/sql/docs/postgres/connect-kubernetes-engine

### Configure service accounts

gcloud iam service-accounts add-iam-policy-binding \
--role="roles/iam.workloadIdentityUser" \
--member="serviceAccount:learngcp-353414.svc.id.goog[default/music-explorer-ksa]" \
music-explorer@learngcp-353414.iam.gserviceaccount.com

kubectl annotate serviceaccount \
music-explorer-ksa \
iam.gke.io/gcp-service-account=music-explorer@learngcp-353414.iam.gserviceaccount.com

gcloud projects add-iam-policy-binding learngcp-353414 --member=serviceAccount:94920321678@cloudbuild.gserviceaccount.com --role=roles/container.developer

gcloud projects add-iam-policy-binding learngcp-353414 --member=serviceAccount:94920321678@cloudbuild.gserviceaccount.com --role=roles/secretmanager.viewer

Useful article: https://dev.to/lehauchicha/google-cloud-platform-deploy-simple-java-spring-boot-application-4f85

gradle update -PliquibasePassword=liquibasePassword -PdbHost=localhost

GRANT ALL
ON ALL TABLES
IN SCHEMA "public"
TO "liquibaseUser";