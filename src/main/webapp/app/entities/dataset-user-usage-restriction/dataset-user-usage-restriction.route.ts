import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';
import { DatasetUserUsageRestrictionService } from './dataset-user-usage-restriction.service';
import { DatasetUserUsageRestrictionComponent } from './dataset-user-usage-restriction.component';
import { DatasetUserUsageRestrictionDetailComponent } from './dataset-user-usage-restriction-detail.component';
import { DatasetUserUsageRestrictionUpdateComponent } from './dataset-user-usage-restriction-update.component';
import { DatasetUserUsageRestrictionDeletePopupComponent } from './dataset-user-usage-restriction-delete-dialog.component';
import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

@Injectable({ providedIn: 'root' })
export class DatasetUserUsageRestrictionResolve implements Resolve<IDatasetUserUsageRestriction> {
  constructor(private service: DatasetUserUsageRestrictionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetUserUsageRestriction> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetUserUsageRestriction>) => response.ok),
        map((datasetUserUsageRestriction: HttpResponse<DatasetUserUsageRestriction>) => datasetUserUsageRestriction.body),
      );
    }
    return of(new DatasetUserUsageRestriction());
  }
}

export const datasetUserUsageRestrictionRoute: Routes = [
  {
    path: '',
    component: DatasetUserUsageRestrictionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetUserUsageRestriction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetUserUsageRestrictionDetailComponent,
    resolve: {
      datasetUserUsageRestriction: DatasetUserUsageRestrictionResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetUserUsageRestriction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetUserUsageRestrictionUpdateComponent,
    resolve: {
      datasetUserUsageRestriction: DatasetUserUsageRestrictionResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetUserUsageRestriction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetUserUsageRestrictionUpdateComponent,
    resolve: {
      datasetUserUsageRestriction: DatasetUserUsageRestrictionResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetUserUsageRestriction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetUserUsageRestrictionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetUserUsageRestrictionDeletePopupComponent,
    resolve: {
      datasetUserUsageRestriction: DatasetUserUsageRestrictionResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetUserUsageRestriction.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
