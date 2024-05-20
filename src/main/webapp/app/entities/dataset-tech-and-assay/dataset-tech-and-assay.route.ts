import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';
import { DatasetTechAndAssayService } from './dataset-tech-and-assay.service';
import { DatasetTechAndAssayComponent } from './dataset-tech-and-assay.component';
import { DatasetTechAndAssayDetailComponent } from './dataset-tech-and-assay-detail.component';
import { DatasetTechAndAssayUpdateComponent } from './dataset-tech-and-assay-update.component';
import { DatasetTechAndAssayDeletePopupComponent } from './dataset-tech-and-assay-delete-dialog.component';
import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

@Injectable({ providedIn: 'root' })
export class DatasetTechAndAssayResolve implements Resolve<IDatasetTechAndAssay> {
  constructor(private service: DatasetTechAndAssayService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDatasetTechAndAssay> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DatasetTechAndAssay>) => response.ok),
        map((datasetTechAndAssay: HttpResponse<DatasetTechAndAssay>) => datasetTechAndAssay.body),
      );
    }
    return of(new DatasetTechAndAssay());
  }
}

export const datasetTechAndAssayRoute: Routes = [
  {
    path: '',
    component: DatasetTechAndAssayComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTechAndAssay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetTechAndAssayDetailComponent,
    resolve: {
      datasetTechAndAssay: DatasetTechAndAssayResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTechAndAssay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetTechAndAssayUpdateComponent,
    resolve: {
      datasetTechAndAssay: DatasetTechAndAssayResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTechAndAssay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetTechAndAssayUpdateComponent,
    resolve: {
      datasetTechAndAssay: DatasetTechAndAssayResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTechAndAssay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const datasetTechAndAssayPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DatasetTechAndAssayDeletePopupComponent,
    resolve: {
      datasetTechAndAssay: DatasetTechAndAssayResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.datasetTechAndAssay.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
