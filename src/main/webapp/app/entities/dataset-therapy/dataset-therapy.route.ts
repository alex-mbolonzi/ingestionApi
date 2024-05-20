import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetTherapy } from 'app/shared/model/dataset-therapy.model';
import { DatasetTherapyService } from './dataset-therapy.service';
import { DatasetTherapyComponent } from './dataset-therapy.component';
import { DatasetTherapyDetailComponent } from './dataset-therapy-detail.component';
import { DatasetTherapyUpdateComponent } from './dataset-therapy-update.component';
import { DatasetTherapyDeletePopupComponent } from './dataset-therapy-delete-dialog.component';
import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';

@Injectable({ providedIn: 'root' })
export class DatasetTherapyResolve implements Resolve<IDatasetTherapy> {
  constructor(private service: DatasetTherapyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetTherapy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetTherapy>) => response.ok),
        map((datasetTherapy: HttpResponse<DatasetTherapy>) => datasetTherapy.body),
      );
    }
    return of(new DatasetTherapy());
  }
}

export const datasetTherapyRoute: Routes = [
  {
    path: '',
    component: DatasetTherapyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetTherapyDetailComponent,
    resolve: {
      datasetTherapy: DatasetTherapyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetTherapyUpdateComponent,
    resolve: {
      datasetTherapy: DatasetTherapyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetTherapyUpdateComponent,
    resolve: {
      datasetTherapy: DatasetTherapyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetTherapyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetTherapyDeletePopupComponent,
    resolve: {
      datasetTherapy: DatasetTherapyResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
