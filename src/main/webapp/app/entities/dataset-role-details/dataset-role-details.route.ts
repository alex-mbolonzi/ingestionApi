import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';
import { DatasetRoleDetailsService } from './dataset-role-details.service';
import { DatasetRoleDetailsComponent } from './dataset-role-details.component';
import { DatasetRoleDetailsDetailComponent } from './dataset-role-details-detail.component';
import { DatasetRoleDetailsUpdateComponent } from './dataset-role-details-update.component';
import { DatasetRoleDetailsDeletePopupComponent } from './dataset-role-details-delete-dialog.component';
import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

@Injectable({ providedIn: 'root' })
export class DatasetRoleDetailsResolve implements Resolve<IDatasetRoleDetails> {
  constructor(private service: DatasetRoleDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetRoleDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetRoleDetails>) => response.ok),
        map((datasetRoleDetails: HttpResponse<DatasetRoleDetails>) => datasetRoleDetails.body),
      );
    }
    return of(new DatasetRoleDetails());
  }
}

export const datasetRoleDetailsRoute: Routes = [
  {
    path: '',
    component: DatasetRoleDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetRoleDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetRoleDetailsDetailComponent,
    resolve: {
      datasetRoleDetails: DatasetRoleDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetRoleDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetRoleDetailsUpdateComponent,
    resolve: {
      datasetRoleDetails: DatasetRoleDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetRoleDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetRoleDetailsUpdateComponent,
    resolve: {
      datasetRoleDetails: DatasetRoleDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetRoleDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetRoleDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetRoleDetailsDeletePopupComponent,
    resolve: {
      datasetRoleDetails: DatasetRoleDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetRoleDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
