# Create Kubernetes cluster
gcloud container --project "learngcp-353414" clusters create-auto "music-explorer-cluster" --region "europe-central2" --release-channel "regular" --network "projects/learngcp-353414/global/networks/default" --subnetwork "projects/learngcp-353414/regions/europe-central2/subnetworks/default" --cluster-ipv4-cidr "/17" --services-ipv4-cidr "/22"

# Bind GCP Service Account to Kubernetes service account
gcloud iam service-accounts add-iam-policy-binding \
--role="roles/iam.workloadIdentityUser" \
--member="serviceAccount:learngcp-353414.svc.id.goog[default/music-explorer-ksa]" \
music-explorer@learngcp-353414.iam.gserviceaccount.com

kubectl annotate serviceaccount \
music-explorer-ksa \
iam.gke.io/gcp-service-account=music-explorer@learngcp-353414.iam.gserviceaccount.com

# Grant permissions to cloud build service account
gcloud projects add-iam-policy-binding learngcp-353414 --member=serviceAccount:94920321678@cloudbuild.gserviceaccount.com --role=roles/container.developer
gcloud projects add-iam-policy-binding learngcp-353414 --member=serviceAccount:94920321678@cloudbuild.gserviceaccount.com --role=roles/secretmanager.viewer
gcloud projects add-iam-policy-binding learngcp-353414 --member=serviceAccount:94920321678@cloudbuild.gserviceaccount.com --role=roles/cloudsql.client
