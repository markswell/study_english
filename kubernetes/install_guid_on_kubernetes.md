# Complete Guide: Setting Up Ingress on Kubernetes Kind for this Application
Detailed step-by-step guide from cluster creation to having the application working with Ingress:

Kubernetes Kind and kubectl were used to test this deployment.

#### Step 1: Create the Kind cluster configuration file
The example kind-config.yaml file can be found in the project's root folder.

#### Step 2: Create the cluster with the configuration
Run:

```bash
kind create cluster --name study-english --config kind-config.yaml
```

#### Step 3: Install the NGINX Ingress Controller

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
```
Verify the installation:

```bash
kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s
```

#### Step 4: Prepare your images
For each image (frontend and backend):

bash >>
bash
docker build -t markswell/study-english-frontend:vkubernetes ../study_english_frontend
docker build -t markswell/study-english-api:vkubernetes ./study-english_backend
Load into the Kind cluster
bash
kind load docker-image markswell/study-english-api:vkubernetes --name study-english
kind load docker-image markswell/study-english-frontend:vkubernetes --name study-english
Step 5: Create Deployments and Services
The deployments.yaml file can be found in the project's root folder.

Apply:

bash >>
bash
kubectl apply -f deployments.yaml
Step 6: Configure Ingress
The ingress.yaml file can be found in the project's root folder.

Apply:

bash >>
bash
kubectl apply -f ingress.yaml
Step 7: Configure /etc/hosts (Optional)
Edit your /etc/hosts file (Linux/Mac) or C:\Windows\System32\drivers\etc\hosts (Windows):

text
127.0.0.1 myapp.local
Step 8: Access the application
You can access it in two ways:

Directly via localhost:

text
http://localhost:8000
Via custom hostname (if configured):

text
http://myapp.local:8000
Step 9: Verify functionality
Check created resources:

bash
kubectl get all
kubectl get ingress
Test endpoints:

bash
### Frontend
curl http://localhost:8000

### Backend
curl http://localhost:8000/api/
Complete Flow Summary
Create Kind cluster with port configuration

Install Ingress Controller

Build and load images into the cluster

Deploy services (frontend and backend)

Configure Ingress for routing

Configure local hosts (optional)

Access the application
