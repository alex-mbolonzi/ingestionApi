import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from './dataset-details.service';
import { DatasetDetailsComponent } from './dataset-details.component';
import { DatasetDetailsDetailComponent } from './dataset-details-detail.component';
import { DatasetDetailsUpdateComponent } from './dataset-details-update.component';
import { DatasetDetailsDeletePopupComponent } from './dataset-details-delete-dialog.component';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';

@Injectable({ providedIn: 'root' })
export class DatasetDetailsResolve implements Resolve<IDatasetDetails> {
  constructor(private service: DatasetDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetDetails>) => response.ok),
        map((datasetDetails: HttpResponse<DatasetDetails>) => datasetDetails.body),
      );
    }
    return of(new DatasetDetails());
  }
}

export const datasetDetailsRoute: Routes = [
  {
    path: '',
    component: DatasetDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetDetailsDetailComponent,
    resolve: {
      datasetDetails: DatasetDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetDetailsUpdateComponent,
    resolve: {
      datasetDetails: DatasetDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetDetailsUpdateComponent,
    resolve: {
      datasetDetails: DatasetDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetDetailsDeletePopupComponent,
    resolve: {
      datasetDetails: DatasetDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
